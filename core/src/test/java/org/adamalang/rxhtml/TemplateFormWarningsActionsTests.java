/*
* Adama Platform and Language
* Copyright (C) 2021 - 2023 by Adama Platform Initiative, LLC
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as published
* by the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Affero General Public License for more details.
* 
* You should have received a copy of the GNU Affero General Public License
* along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package org.adamalang.rxhtml;

public class TemplateFormWarningsActionsTests extends BaseRxHtmlTest {
  @Override
  public boolean dev() {
    return false;
  }
  @Override
  public String issues() {
    StringBuilder issues = new StringBuilder();
    issues.append("WARNING:/:The input 'x' is excessive.");
    issues.append("\nWARNING:/:Failed to find an input for 'email'");
    issues.append("\nWARNING:/:Failed to find an input for 'password'");
    issues.append("\nWARNING:/:The input 'x' is excessive.");
    issues.append("\nWARNING:/:The input 'email' is excessive.");
    issues.append("\nWARNING:/:Failed to find an input for 'password'");
    issues.append("\nWARNING:/:Failed to find an input for 'key'");
    issues.append("\nWARNING:/:Failed to find an input for 'email'");
    issues.append("\nWARNING:/:Failed to find an input for 'password'");
    issues.append("\nWARNING:/:Failed to find an input for 'code'");
    issues.append("\nWARNING:/:Emails should have type 'email'.");
    issues.append("\nWARNING:/:Passwords should have type 'password' or 'hidden'.");
    issues.append("\nWARNING:/:The input 'email' is excessive.");
    issues.append("\nWARNING:/:Passwords should have type 'password' or 'hidden'.");
    issues.append("\nWARNING:/:Failed to find an input for 'username'");
    issues.append("\nWARNING:/:Failed to find an input for 'space'");
    issues.append("\nWARNING:/:Failed to find an input for 'key'");
    issues.append("\nWARNING:/:Failed to find an input for 'username'");
    issues.append("\nWARNING:/:Failed to find an input for 'password'");
    issues.append("\nWARNING:/:Failed to find an input for 'new_password'");
    issues.append("\nWARNING:/:Failed to find an input for 'space'");
    issues.append("\nWARNING:/:Failed to find an input for 'key'");
    issues.append("\nWARNING:/:Failed to find an input for 'username'");
    issues.append("\nWARNING:/:Failed to find an input for 'password'");
    issues.append("\nWARNING:/:Failed to find an input for 'new_password'");
    issues.append("\nWARNING:/:Emails should have type 'email'");
    issues.append("\nWARNING:/:Emails should have type 'email'");
    issues.append("\nWARNING:/:Passwords should have type 'password'.");
    issues.append("\nWARNING:/:Failed to find an input for 'space'");
    issues.append("\nWARNING:/:Failed to find an input for 'key'");
    issues.append("\nWARNING:/:Failed to find an input for 'files'");
    return issues.toString();
  }
  @Override
  public String gold() {
    StringBuilder gold = new StringBuilder();
    gold.append("JavaScript:(function($){");
    gold.append("\n  $.PG(['fixed',''], function(b,a) {");
    gold.append("\n    var c=$.X();");
    gold.append("\n    b.append($.T(' Simple Page '));");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var e=$.RX([]);");
    gold.append("\n    e.rx_forward='/';");
    gold.append("\n    $.aSO(d,a,'default',e);");
    gold.append("\n    var f=[];");
    gold.append("\n    f.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,f);");
    gold.append("\n    var g=[];");
    gold.append("\n    g.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,g);");
    gold.append("\n    var h=$.E('input');");
    gold.append("\n    $.SA(h,'name',\"x\");");
    gold.append("\n    d.append(h);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var h=$.RX([]);");
    gold.append("\n    h.rx_forward='/w00t';");
    gold.append("\n    $.aDSO(d,a,'default',h);");
    gold.append("\n    var i=[];");
    gold.append("\n    i.push($.bS(d,$.pV(a),'docnope',false));");
    gold.append("\n    $.onB(d,'success',a,i);");
    gold.append("\n    var j=[];");
    gold.append("\n    j.push($.bS(d,$.pV(a),'docnope',true));");
    gold.append("\n    $.onB(d,'failure',a,j);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'type',\"hidden\");");
    gold.append("\n    $.SA(k,'name',\"space\");");
    gold.append("\n    k.value=\"s \";");
    gold.append("\n    d.append(k);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'type',\"hidden\");");
    gold.append("\n    $.SA(k,'name',\"x\");");
    gold.append("\n    k.value=\"k\";");
    gold.append("\n    d.append(k);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'name',\"username\");");
    gold.append("\n    $.SA(k,'type',\"username\");");
    gold.append("\n    d.append(k);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'name',\"email\");");
    gold.append("\n    $.SA(k,'type',\"password\");");
    gold.append("\n    d.append(k);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aCC(d,a,'foo');");
    gold.append("\n    var k=[];");
    gold.append("\n    k.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,k);");
    gold.append("\n    var l=[];");
    gold.append("\n    l.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,l);");
    gold.append("\n    var m=$.E('input');");
    gold.append("\n    $.SA(m,'name',\"x\");");
    gold.append("\n    d.append(m);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSU(d,a,'/');");
    gold.append("\n    var m=[];");
    gold.append("\n    m.push($.bS(d,$.pV(a),'sign_up_failed',false));");
    gold.append("\n    $.onB(d,'success',a,m);");
    gold.append("\n    var n=[];");
    gold.append("\n    n.push($.bS(d,$.pV(a),'sign_up_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,n);");
    gold.append("\n    var o=$.E('input');");
    gold.append("\n    $.SA(o,'name',\"x\");");
    gold.append("\n    d.append(o);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSP(d,a,'/');");
    gold.append("\n    var o=[];");
    gold.append("\n    o.push($.bS(d,$.pV(a),'set_password_failed',false));");
    gold.append("\n    $.onB(d,'success',a,o);");
    gold.append("\n    var p=[];");
    gold.append("\n    p.push($.bS(d,$.pV(a),'set_password_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,p);");
    gold.append("\n    var q=$.E('input');");
    gold.append("\n    $.SA(q,'name',\"x\");");
    gold.append("\n    d.append(q);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSD(d,a,'channel');");
    gold.append("\n    var q=[];");
    gold.append("\n    q.push($.bS(d,$.pV(a),'send_failed',false));");
    gold.append("\n    $.onB(d,'success',a,q);");
    gold.append("\n    var r=[];");
    gold.append("\n    r.push($.bS(d,$.pV(a),'send_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,r);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var s=$.RX([]);");
    gold.append("\n    s.rx_forward='/';");
    gold.append("\n    $.aSO(d,a,'default',s);");
    gold.append("\n    var t=[];");
    gold.append("\n    t.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,t);");
    gold.append("\n    var u=[];");
    gold.append("\n    u.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,u);");
    gold.append("\n    var v=$.E('input');");
    gold.append("\n    $.SA(v,'name',\"email\");");
    gold.append("\n    d.append(v);");
    gold.append("\n    var v=$.E('input');");
    gold.append("\n    $.SA(v,'name',\"password\");");
    gold.append("\n    d.append(v);");
    gold.append("\n    var v=$.E('input');");
    gold.append("\n    $.SA(v,'type',\"submit\");");
    gold.append("\n    d.append(v);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var v=$.RX([]);");
    gold.append("\n    v.rx_forward='/';");
    gold.append("\n    $.aDSO(d,a,'default',v);");
    gold.append("\n    var w=[];");
    gold.append("\n    w.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,w);");
    gold.append("\n    var x=[];");
    gold.append("\n    x.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,x);");
    gold.append("\n    var y=$.E('input');");
    gold.append("\n    $.SA(y,'name',\"email\");");
    gold.append("\n    d.append(y);");
    gold.append("\n    var y=$.E('input');");
    gold.append("\n    $.SA(y,'name',\"password\");");
    gold.append("\n    $.SA(y,'type',\"text\");");
    gold.append("\n    d.append(y);");
    gold.append("\n    var y=$.E('input');");
    gold.append("\n    $.SA(y,'type',\"submit\");");
    gold.append("\n    d.append(y);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var y=$.RX([]);");
    gold.append("\n    y.rx_forward='/';");
    gold.append("\n    $.aDSOr(d,a,'default',y);");
    gold.append("\n    var z=[];");
    gold.append("\n    z.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,z);");
    gold.append("\n    var ab=[];");
    gold.append("\n    ab.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,ab);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var bb=$.RX([]);");
    gold.append("\n    bb.rx_forward='/';");
    gold.append("\n    $.adDSOr(d,a,'default',bb);");
    gold.append("\n    var cb=[];");
    gold.append("\n    cb.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,cb);");
    gold.append("\n    var db=[];");
    gold.append("\n    db.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,db);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aCC(d,a,'foo');");
    gold.append("\n    var eb=[];");
    gold.append("\n    eb.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,eb);");
    gold.append("\n    var fb=[];");
    gold.append("\n    fb.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,fb);");
    gold.append("\n    var gb=$.E('input');");
    gold.append("\n    $.SA(gb,'type',\"submit\");");
    gold.append("\n    d.append(gb);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSU(d,a,'/');");
    gold.append("\n    var gb=[];");
    gold.append("\n    gb.push($.bS(d,$.pV(a),'sign_up_failed',false));");
    gold.append("\n    $.onB(d,'success',a,gb);");
    gold.append("\n    var hb=[];");
    gold.append("\n    hb.push($.bS(d,$.pV(a),'sign_up_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,hb);");
    gold.append("\n    var ib=$.E('input');");
    gold.append("\n    $.SA(ib,'name',\"email\");");
    gold.append("\n    d.append(ib);");
    gold.append("\n    var ib=$.E('input');");
    gold.append("\n    $.SA(ib,'type',\"submit\");");
    gold.append("\n    d.append(ib);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSP(d,a,'/');");
    gold.append("\n    var ib=[];");
    gold.append("\n    ib.push($.bS(d,$.pV(a),'set_password_failed',false));");
    gold.append("\n    $.onB(d,'success',a,ib);");
    gold.append("\n    var jb=[];");
    gold.append("\n    jb.push($.bS(d,$.pV(a),'set_password_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,jb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'name',\"email\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'name',\"password\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'name',\"code\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'type',\"submit\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSD(d,a,'channel');");
    gold.append("\n    var kb=[];");
    gold.append("\n    kb.push($.bS(d,$.pV(a),'send_failed',false));");
    gold.append("\n    $.onB(d,'success',a,kb);");
    gold.append("\n    var lb=[];");
    gold.append("\n    lb.push($.bS(d,$.pV(a),'send_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,lb);");
    gold.append("\n    var mb=$.E('input');");
    gold.append("\n    $.SA(mb,'name',\"param\");");
    gold.append("\n    d.append(mb);");
    gold.append("\n    var mb=$.E('input');");
    gold.append("\n    $.SA(mb,'type',\"submit\");");
    gold.append("\n    d.append(mb);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var mb=$.RX([]);");
    gold.append("\n    mb.rx_forward=true;");
    gold.append("\n    $.aUP(d,a,'default',mb);");
    gold.append("\n    var nb=[];");
    gold.append("\n    nb.push($.bS(d,$.pV(a),'asset_upload_failed',false));");
    gold.append("\n    $.onB(d,'success',a,nb);");
    gold.append("\n    var ob=[];");
    gold.append("\n    ob.push($.bS(d,$.pV(a),'asset_upload_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,ob);");
    gold.append("\n    var pb=$.E('input');");
    gold.append("\n    $.SA(pb,'name',\"spacex\");");
    gold.append("\n    d.append(pb);");
    gold.append("\n    var pb=$.E('input');");
    gold.append("\n    $.SA(pb,'name',\"keyx\");");
    gold.append("\n    d.append(pb);");
    gold.append("\n    b.append(d);");
    gold.append("\n  });");
    gold.append("\n})(RxHTML);");
    gold.append("\nStyle:");
    gold.append("\nShell:<!DOCTYPE html>");
    gold.append("\n<html>");
    gold.append("\n<head><script src=\"https://aws-us-east-2.adama-platform.com/libadama.js\"></script><script>");
    gold.append("\n");
    gold.append("\n(function($){");
    gold.append("\n  $.PG(['fixed',''], function(b,a) {");
    gold.append("\n    var c=$.X();");
    gold.append("\n    b.append($.T(' Simple Page '));");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var e=$.RX([]);");
    gold.append("\n    e.rx_forward='/';");
    gold.append("\n    $.aSO(d,a,'default',e);");
    gold.append("\n    var f=[];");
    gold.append("\n    f.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,f);");
    gold.append("\n    var g=[];");
    gold.append("\n    g.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,g);");
    gold.append("\n    var h=$.E('input');");
    gold.append("\n    $.SA(h,'name',\"x\");");
    gold.append("\n    d.append(h);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var h=$.RX([]);");
    gold.append("\n    h.rx_forward='/w00t';");
    gold.append("\n    $.aDSO(d,a,'default',h);");
    gold.append("\n    var i=[];");
    gold.append("\n    i.push($.bS(d,$.pV(a),'docnope',false));");
    gold.append("\n    $.onB(d,'success',a,i);");
    gold.append("\n    var j=[];");
    gold.append("\n    j.push($.bS(d,$.pV(a),'docnope',true));");
    gold.append("\n    $.onB(d,'failure',a,j);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'type',\"hidden\");");
    gold.append("\n    $.SA(k,'name',\"space\");");
    gold.append("\n    k.value=\"s \";");
    gold.append("\n    d.append(k);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'type',\"hidden\");");
    gold.append("\n    $.SA(k,'name',\"x\");");
    gold.append("\n    k.value=\"k\";");
    gold.append("\n    d.append(k);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'name',\"username\");");
    gold.append("\n    $.SA(k,'type',\"username\");");
    gold.append("\n    d.append(k);");
    gold.append("\n    var k=$.E('input');");
    gold.append("\n    $.SA(k,'name',\"email\");");
    gold.append("\n    $.SA(k,'type',\"password\");");
    gold.append("\n    d.append(k);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aCC(d,a,'foo');");
    gold.append("\n    var k=[];");
    gold.append("\n    k.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,k);");
    gold.append("\n    var l=[];");
    gold.append("\n    l.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,l);");
    gold.append("\n    var m=$.E('input');");
    gold.append("\n    $.SA(m,'name',\"x\");");
    gold.append("\n    d.append(m);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSU(d,a,'/');");
    gold.append("\n    var m=[];");
    gold.append("\n    m.push($.bS(d,$.pV(a),'sign_up_failed',false));");
    gold.append("\n    $.onB(d,'success',a,m);");
    gold.append("\n    var n=[];");
    gold.append("\n    n.push($.bS(d,$.pV(a),'sign_up_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,n);");
    gold.append("\n    var o=$.E('input');");
    gold.append("\n    $.SA(o,'name',\"x\");");
    gold.append("\n    d.append(o);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSP(d,a,'/');");
    gold.append("\n    var o=[];");
    gold.append("\n    o.push($.bS(d,$.pV(a),'set_password_failed',false));");
    gold.append("\n    $.onB(d,'success',a,o);");
    gold.append("\n    var p=[];");
    gold.append("\n    p.push($.bS(d,$.pV(a),'set_password_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,p);");
    gold.append("\n    var q=$.E('input');");
    gold.append("\n    $.SA(q,'name',\"x\");");
    gold.append("\n    d.append(q);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSD(d,a,'channel');");
    gold.append("\n    var q=[];");
    gold.append("\n    q.push($.bS(d,$.pV(a),'send_failed',false));");
    gold.append("\n    $.onB(d,'success',a,q);");
    gold.append("\n    var r=[];");
    gold.append("\n    r.push($.bS(d,$.pV(a),'send_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,r);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var s=$.RX([]);");
    gold.append("\n    s.rx_forward='/';");
    gold.append("\n    $.aSO(d,a,'default',s);");
    gold.append("\n    var t=[];");
    gold.append("\n    t.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,t);");
    gold.append("\n    var u=[];");
    gold.append("\n    u.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,u);");
    gold.append("\n    var v=$.E('input');");
    gold.append("\n    $.SA(v,'name',\"email\");");
    gold.append("\n    d.append(v);");
    gold.append("\n    var v=$.E('input');");
    gold.append("\n    $.SA(v,'name',\"password\");");
    gold.append("\n    d.append(v);");
    gold.append("\n    var v=$.E('input');");
    gold.append("\n    $.SA(v,'type',\"submit\");");
    gold.append("\n    d.append(v);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var v=$.RX([]);");
    gold.append("\n    v.rx_forward='/';");
    gold.append("\n    $.aDSO(d,a,'default',v);");
    gold.append("\n    var w=[];");
    gold.append("\n    w.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,w);");
    gold.append("\n    var x=[];");
    gold.append("\n    x.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,x);");
    gold.append("\n    var y=$.E('input');");
    gold.append("\n    $.SA(y,'name',\"email\");");
    gold.append("\n    d.append(y);");
    gold.append("\n    var y=$.E('input');");
    gold.append("\n    $.SA(y,'name',\"password\");");
    gold.append("\n    $.SA(y,'type',\"text\");");
    gold.append("\n    d.append(y);");
    gold.append("\n    var y=$.E('input');");
    gold.append("\n    $.SA(y,'type',\"submit\");");
    gold.append("\n    d.append(y);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var y=$.RX([]);");
    gold.append("\n    y.rx_forward='/';");
    gold.append("\n    $.aDSOr(d,a,'default',y);");
    gold.append("\n    var z=[];");
    gold.append("\n    z.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,z);");
    gold.append("\n    var ab=[];");
    gold.append("\n    ab.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,ab);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var bb=$.RX([]);");
    gold.append("\n    bb.rx_forward='/';");
    gold.append("\n    $.adDSOr(d,a,'default',bb);");
    gold.append("\n    var cb=[];");
    gold.append("\n    cb.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,cb);");
    gold.append("\n    var db=[];");
    gold.append("\n    db.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,db);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aCC(d,a,'foo');");
    gold.append("\n    var eb=[];");
    gold.append("\n    eb.push($.bS(d,$.pV(a),'sign_in_failed',false));");
    gold.append("\n    $.onB(d,'success',a,eb);");
    gold.append("\n    var fb=[];");
    gold.append("\n    fb.push($.bS(d,$.pV(a),'sign_in_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,fb);");
    gold.append("\n    var gb=$.E('input');");
    gold.append("\n    $.SA(gb,'type',\"submit\");");
    gold.append("\n    d.append(gb);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSU(d,a,'/');");
    gold.append("\n    var gb=[];");
    gold.append("\n    gb.push($.bS(d,$.pV(a),'sign_up_failed',false));");
    gold.append("\n    $.onB(d,'success',a,gb);");
    gold.append("\n    var hb=[];");
    gold.append("\n    hb.push($.bS(d,$.pV(a),'sign_up_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,hb);");
    gold.append("\n    var ib=$.E('input');");
    gold.append("\n    $.SA(ib,'name',\"email\");");
    gold.append("\n    d.append(ib);");
    gold.append("\n    var ib=$.E('input');");
    gold.append("\n    $.SA(ib,'type',\"submit\");");
    gold.append("\n    d.append(ib);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSP(d,a,'/');");
    gold.append("\n    var ib=[];");
    gold.append("\n    ib.push($.bS(d,$.pV(a),'set_password_failed',false));");
    gold.append("\n    $.onB(d,'success',a,ib);");
    gold.append("\n    var jb=[];");
    gold.append("\n    jb.push($.bS(d,$.pV(a),'set_password_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,jb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'name',\"email\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'name',\"password\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'name',\"code\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    var kb=$.E('input');");
    gold.append("\n    $.SA(kb,'type',\"submit\");");
    gold.append("\n    d.append(kb);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    $.aSD(d,a,'channel');");
    gold.append("\n    var kb=[];");
    gold.append("\n    kb.push($.bS(d,$.pV(a),'send_failed',false));");
    gold.append("\n    $.onB(d,'success',a,kb);");
    gold.append("\n    var lb=[];");
    gold.append("\n    lb.push($.bS(d,$.pV(a),'send_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,lb);");
    gold.append("\n    var mb=$.E('input');");
    gold.append("\n    $.SA(mb,'name',\"param\");");
    gold.append("\n    d.append(mb);");
    gold.append("\n    var mb=$.E('input');");
    gold.append("\n    $.SA(mb,'type',\"submit\");");
    gold.append("\n    d.append(mb);");
    gold.append("\n    b.append(d);");
    gold.append("\n    var d=$.E('form');");
    gold.append("\n    var mb=$.RX([]);");
    gold.append("\n    mb.rx_forward=true;");
    gold.append("\n    $.aUP(d,a,'default',mb);");
    gold.append("\n    var nb=[];");
    gold.append("\n    nb.push($.bS(d,$.pV(a),'asset_upload_failed',false));");
    gold.append("\n    $.onB(d,'success',a,nb);");
    gold.append("\n    var ob=[];");
    gold.append("\n    ob.push($.bS(d,$.pV(a),'asset_upload_failed',true));");
    gold.append("\n    $.onB(d,'failure',a,ob);");
    gold.append("\n    var pb=$.E('input');");
    gold.append("\n    $.SA(pb,'name',\"spacex\");");
    gold.append("\n    d.append(pb);");
    gold.append("\n    var pb=$.E('input');");
    gold.append("\n    $.SA(pb,'name',\"keyx\");");
    gold.append("\n    d.append(pb);");
    gold.append("\n    b.append(d);");
    gold.append("\n  });");
    gold.append("\n})(RxHTML);");
    gold.append("\n");
    gold.append("\n");
    gold.append("\n</script><style>");
    gold.append("\n");
    gold.append("\n");
    gold.append("\n");
    gold.append("\n</style></head><body></body><script>");
    gold.append("\n  RxHTML.init();");
    gold.append("\n</script></html>");
    return gold.toString();
  }
  @Override
  public String source() {
    StringBuilder source = new StringBuilder();
    source.append("<forest>");
    source.append("\n    <page uri=\"/\">");
    source.append("\n        Simple Page");
    source.append("\n        <form rx:action=\"adama:sign-in\">");
    source.append("\n            <input name=\"x\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"document:sign-in\" rx:failure-variable=\"docnope\" rx:forward=\"/w00t\">");
    source.append("\n            <input type=\"hidden\" name=\"space\" value=\"s \"/>");
    source.append("\n            <input type=\"hidden\" name=\"x\" value=\"k\" />");
    source.append("\n            <input name=\"username\" type=\"username\" />");
    source.append("\n            <input name=\"email\" type=\"password\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"custom:foo\">");
    source.append("\n            <input name=\"x\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"adama:sign-up\">");
    source.append("\n            <input name=\"x\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"adama:set-password\">");
    source.append("\n            <input name=\"x\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"send:channel\">");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"adama:sign-in\">");
    source.append("\n            <input name=\"email\" />");
    source.append("\n            <input name=\"password\" />");
    source.append("\n            <input type=\"submit\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"document:sign-in\">");
    source.append("\n            <input name=\"email\" />");
    source.append("\n            <input name=\"password\" type=\"text\" />");
    source.append("\n            <input type=\"submit\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"document:sign-in-reset\">");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"domain:sign-in-reset\">");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"custom:foo\">");
    source.append("\n            <input type=\"submit\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"adama:sign-up\">");
    source.append("\n            <input name=\"email\" />");
    source.append("\n            <input type=\"submit\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"adama:set-password\">");
    source.append("\n            <input name=\"email\" />");
    source.append("\n            <input name=\"password\" />");
    source.append("\n            <input name=\"code\" />");
    source.append("\n            <input type=\"submit\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"send:channel\">");
    source.append("\n            <input name=\"param\" />");
    source.append("\n            <input type=\"submit\" />");
    source.append("\n        </form>");
    source.append("\n        <form rx:action=\"adama:upload-asset\">");
    source.append("\n            <input name=\"spacex\" />");
    source.append("\n            <input name=\"keyx\" />");
    source.append("\n        </form>");
    source.append("\n    </page>");
    source.append("\n</forest>");
    return source.toString();
  }
  @Override
  public String schema() {
    StringBuilder gold = new StringBuilder();
    gold.append("{");
    gold.append("\n  \"/\" : { }");
    gold.append("\n}");
    return gold.toString();
  }
}
