import React, { Component } from 'react'
import '../../App.css'
import { Menu, Icon } from 'antd';
import { Row, Col, AutoComplete, Badge, Button, Modal, Form, Input } from 'antd';
import { Link } from 'react-router-dom'
import axios from 'axios';
import firebase_con from '../../Config/firebase';
var swal = require('sweetalert');
const username = localStorage.getItem("userName");

class NavBar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            current: 'Challenges',
            dataSource: [],
            modalVisible: false,
            confirmLoading: false,
            owner_id: null,
            organizations: []
        }

        //  this.fetchOrganizations = this.fetchOrganizations.bind(this);
    }

    // renderOption = (item) => {
    //     console.log(`renderOption.item`, item);
    //     return (
    //       <AutoComplete.Option key={item.name} text={item.name}>
    //         <span>{item.name}</span>
    //       </AutoComplete.Option>
    //     );
    //   }


    // onOrganisationSelect = (value,option) => {
    //     console.log("Value:",value)
    //     console.log("Option:",option)
    // }

    componentDidMount() {

        this.setState({
            dataSource: ["Organisation 1","Organisation 2","Organisation 3"],
            owner_id : "1"
        })
    }

    handleClick = (e) => {
        console.log('click ', e);
        this.setState({
            current: e.key,
        });
    }

    createOrgModal = () => {
        this.setState({
            modalVisible: true,
        });
        console.log("\nCreate organization button clicked!!");
    }

    handleOk = () => {
        this.setState({
            ModalText: 'The modal will be closed in one seconds',
            confirmLoading: true,
        });
        setTimeout(() => {
            this.setState({
                modalVisible: false,
                confirmLoading: false,
            });
        }, 1000);
        console.log("\nOkay button of the modal pressed")
    }

    handleCancel = () => {
        console.log('Clicked cancel button');
        this.setState({
            modalVisible: false,
        });
    }

    createOrganization = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                values.owner_id = this.state.owner_id;
                console.log("\nSending the organization obejct data to the backend\n")
                console.log(JSON.stringify(values))
                axios.defaults.withCredentials = true;
                axios.post('http://localhost:8080/hacker/createOrganization', values)
                    .then(response => {
                        console.log("Status Code : ", response.status);

                        if (response.status === 200) {
                            swal("Organization created successfully", "", "success");
                            console.log(JSON.stringify(response.data));
                        }
                        else {
                            swal("There was some error creating the organization", "", "error");
                        }
                    });
            }
        });
    }
    logout = (e) => {
        firebase_con.auth().signOut();
        localStorage.removeItem("userId");
        localStorage.removeItem("userName");
        //this.props.history.push('/login');
    }

    // fetchOrganizations = (e) => {
    //     axios.defaults.withCredentials = true;
    //     axios.get('http://localhost:8080/hacker/getOrganizations')
    //         .then((response) => {
    //             console.log("Status Code : ", response.status);

    //             if (response.status === 200) {
    //                 console.log("Response received from backend");
    //                 console.log(JSON.stringify(response.data.organizations));

    //                 this.setState({
    //                     organizations: response.data.organizations
    //                 });

    //                 console.log("The number of the organizations is" + response.data.organizations.length);
    //                 var org_names = []
    //                 for (let i = 0; i < response.data.organizations.length; i++) {
    //                     org_names.push(response.data.organizations[i].name)
    //                 }
    //                 this.setState({
    //                     dataSource: org_names
    //                 });
    //                 console.log("\nOrganization names : " + org_names)
    //             }
    //             else {
    //                 console.log("There was some error fetching list of organization from the backend")
    //             }
    //         });
    // }

    render() {
        const { modalVisible, confirmLoading } = this.state;
        const { getFieldDecorator } = this.props.form;
        var leftMenuItems = null;
        var rightMenuItems = null;
        if (localStorage.getItem("userId")) {
            leftMenuItems = <Menu
                onClick={this.handleClick}
                selectedKeys={[this.state.current]}
                mode="horizontal"
            >
                <Menu.Item>
                    OpenHack
                </Menu.Item>
                <Menu.Item key="Challenges">
                    <Link to="/challenges">
                        <Icon type="snippets" />Challenges
                </Link>
                </Menu.Item>
                {/* <Menu.Item key="Organisations"> */}
                {/* <Link to="/organisation"> */}
                <Button onClick={this.createOrgModal}> <Icon type="home" /> Create Organisations</Button>

                {/* </Link> */}
                {/* </Menu.Item> */}
            </Menu>

            rightMenuItems = <div>
                <br></br>
                <Row type="flex" justify="end">
                    {/* <Col span={12}>
                        <AutoComplete
                        style={{ width: 200 }}
                        //dataSource = {this.state.dataSource && this.state.dataSource.map(this.renderOption)}
                        dataSource = {this.state.dataSource && this.state.dataSource}
                        //onSelect = {this.onOrganisationSelect}
                        placeholder="Find Organizations"
                        onFocus = {this.fetchOrganizations}
                        allowClear={true}
                        ></AutoComplete>
                    </Col> */}
                    <Col span={6}>
                        <Badge count={4} style={{ backgroundColor: '#52c41a' }}>
                            <Link to="/messages">
                                <Icon type="message" /> Messages
                            </Link>
                        </Badge>
                    </Col>
                    <Col span={6}>
                        <Badge style={{ backgroundColor: '#52c41a' }}>
                            <Link to="/profile">
                                <Icon type="user" /> Hey {username}
                            </Link>
                        </Badge>
                    </Col>
                    <Col span={6}>
                        <Badge style={{ backgroundColor: '#52c41a' }}>
                            <Button onClick={this.logout}><Link to='/login'>
                                <Icon type="logout" /> Logout </Link>
                            </Button>
                        </Badge>
                    </Col>
                </Row>
            </div>
        } else {
            leftMenuItems = <Menu
                onClick={this.handleClick}
                selectedKeys={[this.state.current]}
                mode="horizontal"
            >
                <Menu.Item>
                    OpenHack
            </Menu.Item>
            </Menu>
            rightMenuItems = <div>
                <br></br>
                <Row type="flex" justify="end">
                    <Col span={24}>

                        <Link to="/login">
                            <Icon type="user" /> Login
                        </Link>

                    </Col>
                </Row>
            </div>
        }

        return (
            <div class="pb-4">
                <Row>
                    <Col span={12}>
                        {leftMenuItems}
                        <Modal
                            title="Create a new organization"
                            visible={modalVisible}
                            onOk={this.handleOk}
                            confirmLoading={confirmLoading}
                            onCancel={this.handleCancel}
                        >

                            <Form
                                layout="vertical"
                                onSubmit={this.createOrganization}
                            >
                                <Form.Item label="Organization name">
                                    {getFieldDecorator('organization_name', {
                                        rules: [{ required: true, message: 'Please input the name of the organization' }],
                                    })(<Input />)}
                                </Form.Item>
                                <Form.Item label="Description">
                                    {getFieldDecorator('organization_desc')(<Input type="textarea" rows="3" cols="10" />)}
                                </Form.Item>
                                <Form.Item label="Street Address">
                                    {getFieldDecorator('street')(<Input />)}
                                </Form.Item>
                                <Form.Item label="City">
                                    {getFieldDecorator('city')(<Input />)}
                                </Form.Item>
                                <Form.Item label="State">
                                    {getFieldDecorator('state')(<Input />)}
                                </Form.Item>
                                <Form.Item label="Zip code">
                                    {getFieldDecorator('zip')(<Input />)}
                                </Form.Item>
                                <Form.Item label="Country">
                                    {getFieldDecorator('country')(<Input />)}
                                </Form.Item>
                                <Form.Item>
                                    <Button type="primary" htmlType="submit">Create</Button>
                                </Form.Item>
                            </Form>
                        </Modal>
                    </Col>
                    <Col span={8} offset={4}>
                        {rightMenuItems}
                    </Col>
                </Row>
            </div>
        )
    }
}

export default Form.create()(NavBar);